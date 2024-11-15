using MedicApp.DTO_s;
using Microsoft.Maui.Storage;
using System.Collections.ObjectModel;
using System.Data;
using System.Text;
using System.Text.Json;

namespace MedicApp.Views;

public partial class CreateDiagnosticPage : ContentPage
{
    public ObservableCollection<StatusDTO> Statuses { get; set; } = new ObservableCollection<StatusDTO>();
    public ObservableCollection<string> Patients { get; set; } = new ObservableCollection<string>();
    private List<PatientDTO> _patientsList = new List<PatientDTO>();
    public string CurrentDate { get; set; }
    private long selectedPatientId;
    private int selectedStatusValue;

    private bool isEditMode = false;
    private DiagnosticDTO currentDiagnostic;
    public CreateDiagnosticPage()
	{
		InitializeComponent();
        NavigationPage.SetHasNavigationBar(this, false);
        CurrentDate = DateTime.Now.ToString("yyyy-MM-dd");
        BindingContext = this;
        CreateButton.Text = "Create Diagnostic";

        LoadPatients();

        LoadStatuses();
    }

    public CreateDiagnosticPage(DiagnosticDTO diagnostic) : this()
    {
        isEditMode = true;
        currentDiagnostic = diagnostic;
        LoadDiagnosticData();
        lblTitle.Text = "Edit Diagnostic";
        CreateButton.Text = "Edit Diagnostic";

        LoadPatients().ContinueWith(_ =>
        {
            MainThread.BeginInvokeOnMainThread(LoadDiagnosticData);
        });
    }

    private void LoadDiagnosticData()
    {
        if (currentDiagnostic != null)
        {
            SymptomsEntry.Text = currentDiagnostic.symptoms;
            DiagnosticEntry.Text = currentDiagnostic.diagnostic;
            ObservationEntry.Text = currentDiagnostic.observation;
            DateLabel.Text = DateTimeOffset.FromUnixTimeMilliseconds(currentDiagnostic.diagnosticDate).ToString("yyyy-MM-dd");

            PatientPicker.SelectedIndex = _patientsList.FindIndex(r => r.id == currentDiagnostic.patient.id);
            StatusPicker.SelectedIndex = Statuses.IndexOf(Statuses.FirstOrDefault(s => s.value == currentDiagnostic.status));
        }
    }

    private async Task LoadPatients()
    {
        try
        {
            var client = new HttpClient();
            var backendUrl = "http://10.0.2.2:5000/api/patient/findAll";

            var response = await client.GetAsync(backendUrl);

            if (response.IsSuccessStatusCode)
            {
                var json = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"Roles JSON: {json}");

                var patients = JsonSerializer.Deserialize<List<PatientDTO>>(json);

                if (patients != null && patients.Any())
                {
                    _patientsList.Clear();
                    _patientsList.AddRange(patients);

                    Patients.Clear();
                    foreach (var patient in patients)
                    {
                        Patients.Add(patient.name);
                        Console.WriteLine($"Patient added: {patient.name}");
                    }

                    PatientPicker.ItemsSource = Patients;
                }
            }
            else
            {
                await DisplayAlert("Error", "No se pudieron obtener los pacientes.", "OK");
                Console.WriteLine("Error: No se pudieron obtener los pacientes.");
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
            Console.WriteLine($"Exception: {ex.Message}");
        }
    }

    private void PatientPicker_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (PatientPicker.SelectedIndex != -1)
        {
            var selectedPatient = _patientsList[PatientPicker.SelectedIndex];
            selectedPatientId = selectedPatient.id;
        }
    }

    private void LoadStatuses()
    {

        Statuses.Add(new StatusDTO { name = "Active", value = 1 });
        Statuses.Add(new StatusDTO { name = "Inactive", value = 2 });

        StatusPicker.ItemsSource = Statuses;
    }

    private void StatusPicker_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (StatusPicker.SelectedIndex != -1)
        {
            var selectedStatus = Statuses[StatusPicker.SelectedIndex];
            selectedStatusValue = selectedStatus.value;

            Console.WriteLine($"Selected Status Value: {selectedStatusValue}");
        }
    }

    private void CreateButton_Clicked(object sender, EventArgs e)
    {
        CreateDiagnostic();
    }

    private void DiagnosticButton_Clicked(object sender, EventArgs e)
    {
        Diagnostic();
    }

    private async void CreateDiagnostic()
    {
        var selectedPatient = _patientsList.FirstOrDefault(r => r.id == selectedPatientId);

        if (selectedPatient == null)
        {
            await DisplayAlert("Error", "El paciente seleccionado no es válido.", "OK");
            return;
        }


        var diagnosticDTO = new DiagnosticDTO
        {
            id = isEditMode ? currentDiagnostic.id : 0,
            symptoms = SymptomsEntry.Text,
            diagnostic = DiagnosticEntry.Text,
            observation = ObservationEntry.Text,
            status = selectedStatusValue,
            diagnosticDate = isEditMode ? currentDiagnostic.diagnosticDate : DateTimeOffset.Now.ToUnixTimeMilliseconds(),

            patient = selectedPatient

        };

        var json = JsonSerializer.Serialize(diagnosticDTO);
        Console.WriteLine($"JSON Enviado: {json}");
        var content = new StringContent(json, Encoding.UTF8, "application/json");

        var client = new HttpClient();
        var backendUrl = isEditMode ? $"http://10.0.2.2:5000/api/diagnostic/update/{diagnosticDTO.id}" : "http://10.0.2.2:5000/api/diagnostic/save";

        HttpResponseMessage response;

        if (isEditMode)
        {
            response = await client.PutAsync(backendUrl, content);
        }
        else
        {
            response = await client.PostAsync(backendUrl, content);
        }

        if (response.IsSuccessStatusCode)
        {
            await DisplayAlert("Éxito", isEditMode ? "Diagnostico actualizado con éxito." : "Diagnostico creado con éxito.", "OK");
            await Navigation.PopAsync();
        }
        else
        {
            await DisplayAlert("Error", "Error al guardar el diagnostico.", "OK");
        }
    }

    private async void Diagnostic()
    {
        try
        {
            string symptoms = SymptomsEntry.Text;

            if (string.IsNullOrWhiteSpace(symptoms))
            {
                await DisplayAlert("Error", "Por favor ingresa algunos síntomas.", "OK");
                return;
            }

            var client = new HttpClient();

            string url = $"http://10.0.2.2:5000/api/diagnostic/diagnostic?symptoms={Uri.EscapeDataString(symptoms)}";

            var response = await client.GetAsync(url);

            if (response.IsSuccessStatusCode)
            {
                var diagnosticResult = await response.Content.ReadAsStringAsync();

                DiagnosticEntry.Text = diagnosticResult;
            }
            else
            {
                await DisplayAlert("Error", "No se pudo obtener el diagnóstico. Intenta nuevamente.", "OK");
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
        }
    }

    public void SetReadOnlyMode()
    {

        SymptomsEntry.IsEnabled = false;
        DiagnosticEntry.IsEnabled = false;
        ObservationEntry.IsEnabled = false;
        PatientPicker.IsEnabled = false;
        StatusPicker.IsEnabled = false;
        DiagnosticButton.IsEnabled = false;
        CreateButton.IsVisible = false;

        lblTitle.Text = "Your Diagnostic";
    }
}