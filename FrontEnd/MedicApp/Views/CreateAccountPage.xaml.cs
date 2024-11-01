using MedicApp.DTO_s;
using Microsoft.Maui.Storage;
using System.Collections.ObjectModel;
using System.Data;
using System.Text;
using System.Text.Json;

namespace MedicApp.Views;

public partial class CreateAccountPage : ContentPage
{
    public ObservableCollection<string> Roles { get; set; } = new ObservableCollection<string>();
    public ObservableCollection<StatusDTO> Statuses { get; set; } = new ObservableCollection<StatusDTO>();
    private List<RoleDTO> _rolesList = new List<RoleDTO>();
    public string CurrentDate { get; set; }
    private long selectedRoleId;
    private int selectedStatusValue;

    private bool isEditMode = false;
    private UserDTO currentUser;
    public CreateAccountPage()
	{
		InitializeComponent();

        CurrentDate = DateTime.Now.ToString("yyyy-MM-dd");
        BindingContext = this;
        CreateButton.Text = "Create Account";

        LoadRoles();

        LoadStatuses();
    }


    public CreateAccountPage(UserDTO user) : this()
    {
        isEditMode = true;
        currentUser = user;
        LoadUserData();
        lblTitle.Text = "Edit User";
        CreateButton.Text = "Edit User";

        LoadRoles().ContinueWith(_ =>
        {
            MainThread.BeginInvokeOnMainThread(LoadUserData);
        });
    }

    private void LoadUserData()
    {
        if (currentUser != null)
        {
            IdentificationEntry.Text = currentUser.identification;
            NameEntry.Text = currentUser.name;
            LastNameEntry.Text = currentUser.lastName;
            UserEntry.Text = currentUser.username;
            EmailEntry.Text = currentUser.email;
            PasswordEntry.Text = currentUser.password;
            DateLabel.Text = DateTimeOffset.FromUnixTimeMilliseconds(currentUser.dateCreate).ToString("yyyy-MM-dd");

            RolePicker.SelectedIndex = _rolesList.FindIndex(r => r.id == currentUser.role.id);
            StatusPicker.SelectedIndex = Statuses.IndexOf(Statuses.FirstOrDefault(s => s.value == currentUser.status));
        }
    }

    private async Task LoadRoles()
    {
        try
        {
            var client = new HttpClient();
            var backendUrl = "http://10.0.2.2:5000/api/role/findAll";

            var response = await client.GetAsync(backendUrl);

            if (response.IsSuccessStatusCode)
            {
                var json = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"Roles JSON: {json}");

                var roles = JsonSerializer.Deserialize<List<RoleDTO>>(json);

                if (roles != null && roles.Any())
                {
                    _rolesList.Clear();
                    _rolesList.AddRange(roles);

                    Roles.Clear();
                    foreach (var role in roles)
                    {
                        Roles.Add(role.name);
                        Console.WriteLine($"Role added: {role.name}");
                    }

                    RolePicker.ItemsSource = Roles;
                }
            }
            else
            {
                await DisplayAlert("Error", "No se pudieron obtener los roles.", "OK");
                Console.WriteLine("Error: No se pudieron obtener los roles.");
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
            Console.WriteLine($"Exception: {ex.Message}");
        }
    }

    private void RolePicker_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (RolePicker.SelectedIndex != -1)
        {
            var selectedRole = _rolesList[RolePicker.SelectedIndex];
            selectedRoleId = selectedRole.id;
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
        CreateUser();
    }

    private async void CreateUser()
    {
        var selectedRole = _rolesList.FirstOrDefault(r => r.id == selectedRoleId);

        if (selectedRole == null)
        {
            await DisplayAlert("Error", "El rol seleccionado no es válido.", "OK");
            return;
        }


        var userDto = new UserDTO
        {
            id = isEditMode ? currentUser.id : 0,
            name = NameEntry.Text,
            lastName = LastNameEntry.Text,
            email = EmailEntry.Text,
            password = PasswordEntry.Text,
            identification = IdentificationEntry.Text,
            username = UserEntry.Text,
            status = selectedStatusValue,
            dateCreate = isEditMode ? currentUser.dateCreate : DateTimeOffset.Now.ToUnixTimeMilliseconds(),

            role = selectedRole
            
        };

        var json = JsonSerializer.Serialize(userDto);
        Console.WriteLine($"JSON Enviado: {json}");
        var content = new StringContent(json, Encoding.UTF8, "application/json");

        var client = new HttpClient();
        var backendUrl = isEditMode ? $"http://10.0.2.2:5000/api/user/update/{userDto.id}" : "http://10.0.2.2:5000/api/user/save";

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
            await DisplayAlert("Éxito", isEditMode ? "Usuario actualizado con éxito." : "Usuario creado con éxito.", "OK");
            await Navigation.PopAsync();
        }
        else
        {
            await DisplayAlert("Error", "Error al guardar el usuario.", "OK");
        }
    }


}