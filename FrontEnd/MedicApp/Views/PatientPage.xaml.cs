using MedicApp.DTO_s;
using System.Collections.ObjectModel;
using System.Text.Json;
using System.Windows.Input;

namespace MedicApp.Views;

public partial class PatientPage : ContentPage
{
    public ObservableCollection<PatientDTO> PatientsList { get; set; } = new ObservableCollection<PatientDTO>();
    public ICommand EditCommand { get; set; }
    public ICommand DeleteCommand { get; set; }
    public PatientPage()
	{
        InitializeComponent();
        BindingContext = this;

        EditCommand = new Command<PatientDTO>(OnEditPatient);
        DeleteCommand = new Command<PatientDTO>(OnDeletePatient);

        LoadPatients();
    }
    protected override void OnAppearing()
    {
        base.OnAppearing();

        LoadPatients();
    }
    private async void LoadPatients()
    {
        try
        {
            var client = new HttpClient();
            var response = await client.GetAsync("http://10.0.2.2:5000/api/patient/findAll");

            if (response.IsSuccessStatusCode)
            {
                var json = await response.Content.ReadAsStringAsync();
                var patients = JsonSerializer.Deserialize<List<PatientDTO>>(json);
                Console.WriteLine($"Número de pacientes obtenidos: {patients.Count}");
                PatientsList.Clear();
                foreach (var patient in patients)
                {
                    PatientsList.Add(patient);
                    Console.WriteLine($"Paciente: {patient.name}, Estado: {patient.status}");
                }
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
        }
    }

    private async void OnEditPatient(PatientDTO patient)
    {
        await Navigation.PushAsync(new CreatePatientPage(patient));
    }

    private async void OnDeletePatient(PatientDTO patient)
    {
        var confirm = await DisplayAlert("Confirmar", "¿Seguro que deseas eliminar este paciente?", "Sí", "No");
        if (confirm)
        {

            try
            {
                var client = new HttpClient();
                var response = await client.DeleteAsync($"http://10.0.2.2:5000/api/patient/delete/{patient.id}");

                if (response.IsSuccessStatusCode)
                {
                    PatientsList.Remove(patient);
                }
                else
                {
                    await DisplayAlert("Error", "No se pudo eliminar el paciente.", "OK");
                }
            }
            catch (Exception ex)
            {
                await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
            }
        }
    }

    private async void btnCreate_Clicked(object sender, EventArgs e)
    {
        await Navigation.PushAsync(new CreatePatientPage());

    }
}