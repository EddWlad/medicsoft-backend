using MedicApp.DTO_s;
using System.Collections.ObjectModel;
using System.Text.Json;
using System.Windows.Input;

namespace MedicApp.Views;

public partial class UsersPage : ContentPage
{
    public ObservableCollection<UserDTO> UsersList { get; set; } = new ObservableCollection<UserDTO>();
    public ICommand EditCommand { get; set; }
    public ICommand DeleteCommand { get; set; }
    public UsersPage()
	{
		InitializeComponent();
        BindingContext = this;

        EditCommand = new Command<UserDTO>(OnEditUser);
        DeleteCommand = new Command<UserDTO>(OnDeleteUser);

        LoadUsers();
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();

        LoadUsers();
    }

    private void AddUser_Clicked(object sender, EventArgs e)
    {

    }

    private async void LoadUsers()
    {
        try
        {
            var client = new HttpClient();
            var response = await client.GetAsync("http://10.0.2.2:5000/api/user/findAll");

            if (response.IsSuccessStatusCode)
            {
                var json = await response.Content.ReadAsStringAsync();
                var users = JsonSerializer.Deserialize<List<UserDTO>>(json);
                Console.WriteLine($"Número de usuarios obtenidos: {users.Count}");
                UsersList.Clear();
                foreach (var user in users)
                {
                    UsersList.Add(user);
                    Console.WriteLine($"Usuario: {user.name}, Estado: {user.status}");
                }
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
        }
    }

    private async void OnEditUser(UserDTO user)
    {
        await Navigation.PushAsync(new CreateAccountPage(user));
    }

    private async void OnDeleteUser(UserDTO user)
    {
        var confirm = await DisplayAlert("Confirmar", "¿Seguro que deseas eliminar este usuario?", "Sí", "No");
        if (confirm)
        {

            try
            {
                var client = new HttpClient();
                var response = await client.DeleteAsync($"http://10.0.2.2:5000/api/user/delete/{user.id}");

                if (response.IsSuccessStatusCode)
                {
                    UsersList.Remove(user);
                }
                else
                {
                    await DisplayAlert("Error", "No se pudo eliminar el usuario.", "OK");
                }
            }
            catch (Exception ex)
            {
                await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
            }
        }
    }
}