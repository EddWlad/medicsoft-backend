

using System.Text.Json;
using System.Text;

namespace MedicApp.Views;

public partial class LoginPage : ContentPage
{
	public LoginPage()
	{
		InitializeComponent();
	}



    private void LoginButton_Clicked(object sender, EventArgs e)
    {
        Console.WriteLine("Login button clicked!");
        string email = EmailEntry.Text;
        string password = PasswordEntry.Text;


        if (string.IsNullOrWhiteSpace(email) || string.IsNullOrWhiteSpace(password))
        {
            DisplayAlert("Error", "Por favor ingresa el email y la contraseña.", "OK");
            return;
        }

        bool isSuccess = Login(email, password);

        if (isSuccess)
        {
            DisplayAlert("Éxito", "Login exitoso", "OK");

        }
        else
        {
            DisplayAlert("Error", "Email o contraseña incorrectos", "OK");
        }
    }

    bool Login(string email, string password)
    {
        try
        {
            // Crear el cliente HTTP
            var client = new HttpClient();
            var backendUrl = "http://10.0.2.2:5000/api/user/login";  // Dirección del backend

            Console.WriteLine($"Enviando solicitud de login con email: {email}");

            // Crear el contenido JSON
            var loginData = new
            {
                email = email,
                password = password
            };

            var json = JsonSerializer.Serialize(loginData);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            // Hacer la solicitud de forma sincrónica usando .Result
            var response = client.PostAsync(backendUrl, content).Result;  // Usamos .Result para hacerlo sincrónico

            // Verificar si la respuesta fue exitosa
            if (response.IsSuccessStatusCode)
            {
                var responseContent = response.Content.ReadAsStringAsync().Result;

                // Verificar si la respuesta es "Login successful"
                if (responseContent.Contains("Login successful"))
                {
                    return true;
                }
                else
                {
                    Console.WriteLine("Login exitoso, datos de usuario: " + responseContent);
                    return true;  // Puedes manejar este caso dependiendo de tu lógica
                }
            }
            else if (response.StatusCode == System.Net.HttpStatusCode.Unauthorized)
            {
                Console.WriteLine("Contraseña incorrecta");
            }
            else if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
            {
                Console.WriteLine("Usuario no encontrado");
            }
        }
        catch (Exception ex)
        {
            DisplayAlert("Error", $"Ocurrió un error: {ex.Message}", "OK");
        }

        return false;  // Si hubo un error, retornar false
    }

    private void CreateButton_Clicked(object sender, EventArgs e)
    {

    }

}