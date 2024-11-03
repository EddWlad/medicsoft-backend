namespace MedicApp.Views;

public partial class AppShell : Shell
{
	public AppShell()
	{
		InitializeComponent();
	}

    private void MenuItem_Clicked(object sender, EventArgs e)
    {
        Application.Current.MainPage = new NavigationPage(new LoginPage());
    }
}