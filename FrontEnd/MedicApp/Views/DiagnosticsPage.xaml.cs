using MedicApp.DTO_s;
using MedicApp.Services;
using System.Collections.ObjectModel;
using System.Text.Json;
using System.Windows.Input;

namespace MedicApp.Views;

public partial class DiagnosticsPage : ContentPage
{
    public ObservableCollection<DiagnosticDTO> DiagnosticsList { get; set; } = new ObservableCollection<DiagnosticDTO>();
    public ICommand EditCommand { get; set; }
    public ICommand DeleteCommand { get; set; }

    public string UserRole { get; private set; }
    public bool IsPatientRole => UserRole == "Paciente";

    public DiagnosticsPage()
	{
		InitializeComponent();
        BindingContext = this;

        var user = SessionService.Instance.GetUser();
        UserRole = user?.role?.name;

        if (IsPatientRole)
        {
            EditCommand = new Command<DiagnosticDTO>(OnViewPatient);
            DeleteCommand = null;
            btnCreate.IsVisible = false;
        }
        else
        {

            EditCommand = new Command<DiagnosticDTO>(OnEditPatient);
            DeleteCommand = new Command<DiagnosticDTO>(OnDeletePatient);
        }

        LoadPatients();
    }

    protected override async void OnAppearing()
    {
        base.OnAppearing();

        var user = SessionService.Instance.GetUser();
        if (user?.role?.name == "Paciente")
        {
            await LoadPatients();

            bool hasNewDiagnostics = DiagnosticsList.Any(d => d.isNew);
            if (hasNewDiagnostics)
            {
                await DisplayAlert("Notificación", "Usted tiene nuevos diagnósticos.", "OK");
                await MarkDiagnosticsAsSeen(user.id);
            }
        }
        else
        {
            LoadPatients();
        }
    }

    private async Task MarkDiagnosticsAsSeen(long patientId)
    {
        var client = new HttpClient();
        string url = $"http://10.0.2.2:5000/api/diagnostic/markAsSeen/{patientId}";

        var response = await client.PutAsync(url, null);
        if (!response.IsSuccessStatusCode)
        {
            await DisplayAlert("Error", "No se pudo marcar los diagnósticos como vistos.", "OK");
        }
        else
        {

            foreach (var diagnostic in DiagnosticsList)
            {
                diagnostic.isNew = false;
            }
        }
    }

    private async void btnCreate_Clicked(object sender, EventArgs e)
    {
        await Navigation.PushAsync(new CreateDiagnosticPage());
    }

    private async Task LoadPatients()
    {
        try
        {
            var client = new HttpClient();
            HttpResponseMessage response;


            var user = SessionService.Instance.GetUser();
            if (user?.role?.name == "Paciente")
            {

                string url = $"http://10.0.2.2:5000/api/diagnostic/byPatient?name={Uri.EscapeDataString(user.name)}&lastName={Uri.EscapeDataString(user.lastName)}";
                response = await client.GetAsync(url);
            }
            else
            {
                response = await client.GetAsync("http://10.0.2.2:5000/api/diagnostic/findAll");
            }

            if (response.IsSuccessStatusCode)
            {
                var json = await response.Content.ReadAsStringAsync();
                var diagnostics = JsonSerializer.Deserialize<List<DiagnosticDTO>>(json);
                Console.WriteLine($"Número de diagnósticos obtenidos: {diagnostics.Count}");
                DiagnosticsList.Clear();
                foreach (var diagnostic in diagnostics)
                {
                    DiagnosticsList.Add(diagnostic);
                    Console.WriteLine($"Paciente: {diagnostic.patient.name}, Estado: {diagnostic.status}");
                }
            }
            else
            {
                await DisplayAlert("Error", "No se pudieron cargar los diagnósticos.", "OK");
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
        }
    }


    private async void OnEditPatient(DiagnosticDTO diagnostic)
    {
        await Navigation.PushAsync(new CreateDiagnosticPage(diagnostic));
    }

    private async void OnDeletePatient(DiagnosticDTO diagnostic)
    {
        var confirm = await DisplayAlert("Confirmar", "¿Seguro que deseas eliminar este diagnostico?", "Sí", "No");
        if (confirm)
        {

            try
            {
                var client = new HttpClient();
                var response = await client.DeleteAsync($"http://10.0.2.2:5000/api/diagnostic/delete/{diagnostic.id}");

                if (response.IsSuccessStatusCode)
                {
                    DiagnosticsList.Remove(diagnostic);
                }
                else
                {
                    await DisplayAlert("Error", "No se pudo eliminar el diagnostico.", "OK");
                }
            }
            catch (Exception ex)
            {
                await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
            }
        }
    }

    private async void OnViewPatient(DiagnosticDTO diagnostic)
    {

        var detailPage = new CreateDiagnosticPage(diagnostic);
        detailPage.SetReadOnlyMode();
        await Navigation.PushAsync(detailPage);
        diagnostic.isNew=false;
    }
}