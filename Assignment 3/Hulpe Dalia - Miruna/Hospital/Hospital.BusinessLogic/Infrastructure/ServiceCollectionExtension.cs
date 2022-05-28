using Hospital.BusinessLogic.Implementations;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models.Settings;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Hospital.Repository.Infrastructure
{
    public static class ServiceCollectionExtension
    {
        public static void AddBusinessLogic(this IServiceCollection services, IConfiguration configuration)
        {
            services.AddTransient<ILocalizationLogic, LocalizationLogic>();

            services.AddScoped<IAppointmentLogic, AppointmentLogic>();

            services.AddScoped<IDepartmentLogic, DepartmentLogic>();

            services.AddScoped<IHospitalLogic, HospitalLogic>();
           
            services.AddScoped<IDepartmentHospitalLogic, DepartmentHospitalLogic>();

            services.AddScoped<IUserLogic, UserLogic>();

            services.AddSingleton<IEmailSenderLogic, EmailSenderLogic>();

            var emailSettings = new EmailSettings();
            configuration.Bind("EmailSettings", emailSettings);
            services.AddSingleton(emailSettings);

            services.AddRepository();
        }
    }
}
