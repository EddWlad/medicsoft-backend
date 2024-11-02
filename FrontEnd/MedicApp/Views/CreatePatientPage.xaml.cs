using MedicApp.DTO_s;
using Microsoft.Maui.Storage;
using System.Collections.ObjectModel;
using System.Data;
using System.Text;
using System.Text.Json;

namespace MedicApp.Views;
public partial class CreatePatientPage : ContentPage
{
    public ObservableCollection<StatusDTO> Statuses { get; set; } = new ObservableCollection<StatusDTO>();
    public ObservableCollection<GenderDTO> Gender { get; set; } = new ObservableCollection<GenderDTO>();

    private int selectedStatusValue;
    private int selectedGenderValue;
    private bool isEditMode = false;
    private PatientDTO currentPatient;
    public CreatePatientPage()
	{
		InitializeComponent();

        BindingContext = this;
        CreateButton.Text = "Create Patient";

        LoadStatuses();
        LoadGender();
    }

    public CreatePatientPage(PatientDTO patient) : this()
    {
        isEditMode = true;
        currentPatient = patient;
        LoadPatientData();
        lblTitle.Text = "Edit Patient";
        CreateButton.Text = "Edit Patient";

    }

    private void LoadPatientData()
    {
        if (currentPatient != null)
        {
            NameEntry.Text = currentPatient.name;
            LastNameEntry.Text = currentPatient.lastName;
            DepartmentEntry.Text = currentPatient.department;
            SizeEntry.Text = currentPatient.size.ToString();
            WeightEntry.Text = currentPatient.weight.ToString();
            YearsEntry.Text = currentPatient.year.ToString();
            ObservationEntry.Text = currentPatient.observation;

            GenderPicker.SelectedIndex = Gender.IndexOf(Gender.FirstOrDefault(r => r.value == currentPatient.gender));
            StatusPicker.SelectedIndex = Statuses.IndexOf(Statuses.FirstOrDefault(s => s.value == currentPatient.status));
        }
    }

    private void LoadStatuses()
    {

        Statuses.Add(new StatusDTO { name = "Active", value = 1 });
        Statuses.Add(new StatusDTO { name = "Inactive", value = 2 });

        StatusPicker.ItemsSource = Statuses;
    }

    private void LoadGender()
    {

        Gender.Add(new GenderDTO { name = "Masculino", value = 1 });
        Gender.Add(new GenderDTO { name = "Femenino", value = 2 });

        GenderPicker.ItemsSource = Gender;
    }

    private void GenderPicker_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (GenderPicker.SelectedIndex != -1)
        {
            var selectedGender = Gender[GenderPicker.SelectedIndex];
            selectedGenderValue = selectedGender.value;

            Console.WriteLine($"Selected Gender Value: {selectedGenderValue}");
        }
    }

    private void CreateButton_Clicked(object sender, EventArgs e)
    {
        CreatePatient();
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

    public float converter(string text)
    {
        float result = 0;
        if (!float.TryParse(text, out result))
        {
            result = 0;
        }
        return result;
    }

    private async void CreatePatient()
    {


        var patientDto = new PatientDTO
        {
            id = isEditMode ? currentPatient.id : 0,
            name = NameEntry.Text,
            lastName = LastNameEntry.Text,
            department = DepartmentEntry.Text,
            size =  converter(SizeEntry.Text),
            weight = converter(WeightEntry.Text),
            year = int.TryParse(YearsEntry.Text, out int parsedYear) ? parsedYear : 0,
            observation = ObservationEntry.Text,
            status = selectedStatusValue,
            gender = selectedGenderValue
        };

        var json = JsonSerializer.Serialize(patientDto);
        Console.WriteLine($"JSON Enviado: {json}");
        var content = new StringContent(json, Encoding.UTF8, "application/json");

        var client = new HttpClient();
        var backendUrl = isEditMode ? $"http://10.0.2.2:5000/api/patient/update/{patientDto.id}" : "http://10.0.2.2:5000/api/patient/save";

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
            await DisplayAlert("Éxito", isEditMode ? "Paciente actualizado con éxito." : "Paciente creado con éxito.", "OK");
            await Navigation.PopAsync();
        }
        else
        {
            await DisplayAlert("Error", "Error al guardar el paciente.", "OK");
        }
    }

    private void SizeEntry_TextChanged(object sender, TextChangedEventArgs e)
    {
        var entry = sender as Entry;
        if (entry == null)
            return;

        string newText = string.Concat(entry.Text.Where(c => char.IsDigit(c) || c == '.'));

        int firstDotIndex = newText.IndexOf('.');
        if (firstDotIndex != -1 && newText.Substring(firstDotIndex + 1).Contains('.'))
        {
            newText = newText.Remove(newText.LastIndexOf('.'));
        }

        if (entry.Text != newText)
        {
            entry.Text = newText;
        }
    }
}