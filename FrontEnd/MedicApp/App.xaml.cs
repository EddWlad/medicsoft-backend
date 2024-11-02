using MedicApp.Views;

namespace MedicApp
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();

            MainPage = new NavigationPage(new DiagnosticsPage());
            //MainPage = new NavigationPage(new PatientPage());
            //MainPage = new NavigationPage(new LoginPage());
        }
    }
}
