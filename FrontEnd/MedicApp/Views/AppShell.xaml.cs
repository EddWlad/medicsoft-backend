using MedicApp.Services;

namespace MedicApp.Views;

public partial class AppShell : Shell
{
	public AppShell()
	{
		InitializeComponent();
        ConfigureShellForUserRole();
    }

    private void ConfigureShellForUserRole()
    {
        var user = SessionService.Instance.GetUser();

        Items.Clear(); 


        var diagnosticPage = new ShellContent
        {
            ContentTemplate = new DataTemplate(typeof(DiagnosticsPage)),
            Title = "Diagnósticos",
            Icon = new FontImageSource
            {
                FontFamily = "AwesomeSolid",
                Glyph = "\uf0f1",
                Color = Colors.Black
            }
        };


        if (user?.role?.name == "Paciente")
        {
            Items.Add(diagnosticPage);
            AddLogoutMenu();
        }
        else
        {
            var patientPage = new ShellContent
            {
                ContentTemplate = new DataTemplate(typeof(PatientPage)),
                Title = "Pacientes",
                Icon = new FontImageSource
                {
                    FontFamily = "AwesomeSolid",
                    Glyph = "\uf2be",
                    Color = Colors.Black
                }
            };

            var usersPage = new ShellContent
            {
                ContentTemplate = new DataTemplate(typeof(UsersPage)),
                Title = "Usuarios",
                Icon = new FontImageSource
                {
                    FontFamily = "AwesomeSolid",
                    Glyph = "\uf0c0",
                    Color = Colors.Black
                }
            };

            Items.Add(diagnosticPage);
            Items.Add(patientPage);
            Items.Add(usersPage);
            AddLogoutMenu();
        }
    }

    private void AddLogoutMenu()
    {

        var logoutMenuItem = new MenuItem
        {
            Text = "Cerrar Sesión",
            IconImageSource = new FontImageSource
            {
                FontFamily = "AwesomeSolid",
                Glyph = "\uf2f5",
                Color = Colors.Black
            }
        };
        logoutMenuItem.Clicked += MenuItem_Clicked;
        Items.Add(logoutMenuItem);
    }

    private void MenuItem_Clicked(object sender, EventArgs e)
    {
        SessionService.Instance.ClearUser();
        Application.Current.MainPage = new NavigationPage(new LoginPage());
    }
}