

using System.Text.Json;
using System.Text;
using MedicApp.Services;
using MedicApp.DTO_s;

namespace MedicApp.Views;

public partial class LoginPage : ContentPage
{
	public LoginPage()
	{
		InitializeComponent();
	}

    private async Task<UserDTO> Login(string email, string password)
    {
        try
        {
            var client = new HttpClient();
            var backendUrl = "http://10.0.2.2:5000/api/user/login";

            var loginData = new
            {
                email = email,
                password = password
            };

            var json = JsonSerializer.Serialize(loginData);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await client.PostAsync(backendUrl, content);

            if (response.IsSuccessStatusCode)
            {
                var responseContent = await response.Content.ReadAsStringAsync();
                // Deserializamos la respuesta para obtener el objeto UserDTO
                return JsonSerializer.Deserialize<UserDTO>(responseContent);
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
        }

        return null;
    }

    private async void LoginButton_Clicked(object sender, EventArgs e)
    {
        Console.WriteLine("Login button clicked!");
        string email = EmailEntry.Text;
        string password = PasswordEntry.Text;


        if (string.IsNullOrWhiteSpace(email) || string.IsNullOrWhiteSpace(password))
        {
            DisplayAlert("Error", "Por favor ingresa el email y la contraseña.", "OK");
            return;
        }


        UserDTO user = await Login(email, password);

        if (user != null)
        {

            SessionService.Instance.SetUser(user);

            await DisplayAlert("Éxito", "Login exitoso", "OK");
            Application.Current.MainPage = new AppShell();
        }
        else
        {
            await DisplayAlert("Error", "Email o contraseña incorrectos", "OK");
        }
    }

    private void CreateButton_Clicked(object sender, EventArgs e)
    {
        Navigation.PushAsync(new CreateAccountPage());
    }

    protected override bool OnBackButtonPressed() => true;

}