using MedicApp.DTO_s;
using MedicApp.Views;
using Microsoft.Extensions.Logging;
using SkiaSharp.Views.Maui.Controls.Hosting;

namespace MedicApp
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .UseSkiaSharp()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                    fonts.AddFont("lato-bold.ttf", "bold");
                    fonts.AddFont("lato-regular.ttf", "medium");
                    fonts.AddFont("Font Awesome 6 Free-Solid-900.otf", "AwesomeSolid");
                });

#if DEBUG
    		builder.Logging.AddDebug();
#endif
            builder.Services.AddTransient<DiagnosticsPage>();
            builder.Services.AddTransient<PatientPage>();
            builder.Services.AddTransient<UsersPage>();

            builder.Services.AddTransient<DiagnosticDTO>();
            builder.Services.AddTransient<GenderDTO>();
            builder.Services.AddTransient<PatientDTO>();
            builder.Services.AddTransient<RoleDTO>();
            builder.Services.AddTransient<StatusDTO>();
            builder.Services.AddTransient<UserDTO>();

            return builder.Build();
        }
    }
}
