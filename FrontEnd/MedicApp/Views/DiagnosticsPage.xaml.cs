using MedicApp.DTO_s;
using System.Collections.ObjectModel;
using System.Text.Json;
using System.Windows.Input;

namespace MedicApp.Views;

public partial class DiagnosticsPage : ContentPage
{
    public ObservableCollection<DiagnosticDTO> DiagnosticsList { get; set; } = new ObservableCollection<DiagnosticDTO>();
    public ICommand EditCommand { get; set; }
    public ICommand DeleteCommand { get; set; }
    public DiagnosticsPage()
	{
		InitializeComponent();
        BindingContext = this;

        EditCommand = new Command<DiagnosticDTO>(OnEditPatient);
        DeleteCommand = new Command<DiagnosticDTO>(OnDeletePatient);

        LoadPatients();
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();

        LoadPatients();
    }


    private async void btnCreate_Clicked(object sender, EventArgs e)
    {
        await Navigation.PushAsync(new CreateDiagnosticPage());
    }

    private async void LoadPatients()
    {
        try
        {
            var client = new HttpClient();
            var response = await client.GetAsync("http://10.0.2.2:5000/api/diagnostic/findAll");

            if (response.IsSuccessStatusCode)
            {
                var json = await response.Content.ReadAsStringAsync();
                var diagnostics = JsonSerializer.Deserialize<List<DiagnosticDTO>>(json);
                Console.WriteLine($"Número de diagnosticos obtenidos: {diagnostics.Count}");
                DiagnosticsList.Clear();
                foreach (var diagnostic in diagnostics)
                {
                    DiagnosticsList.Add(diagnostic);
                    Console.WriteLine($"Usuario: {diagnostic.patient.name}, Estado: {diagnostic.status}");
                }
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
}