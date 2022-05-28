using Hospital.Repository.Implementations;
using Hospital.Repository.Interfaces;
using Microsoft.Extensions.DependencyInjection;

namespace Hospital.Repository.Infrastructure
{
    public static class ServiceCollectionExtension
    {
        public static void AddRepository(this IServiceCollection services)
        {
            services.AddScoped<IUserRepository, UserRepository>();

            services.AddScoped<ILocalizationResourceRepository, LocalizationResourceRepository>();

            services.AddScoped<IAppointmentRepository, AppointmentRepository>();

            services.AddScoped<IDepartmentRepository, DepartmentRepository>();

            services.AddScoped<IHospitalRepository, HospitalRepository>();

            services.AddScoped<IDepartmentHospitalRepository, DepartmentHospitalRepository>();
        }
    }
}
