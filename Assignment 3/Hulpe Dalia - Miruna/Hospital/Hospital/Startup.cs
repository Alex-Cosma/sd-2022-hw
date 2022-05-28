using Hospital.Data;
using Hospital.Localizer;
using Hospital.Repository.Entitys;
using Hospital.Repository.Infrastructure;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Localization;
using Microsoft.AspNetCore.Mvc.Razor;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Localization;
using Microsoft.Extensions.Options;
using System;
using System.Globalization;
using System.Linq;

namespace Hospital
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddDbContext<ApplicationDbContext>(options =>
                options.UseSqlServer(
                    Configuration.GetConnectionString("DefaultConnection")));

            services.AddMvc()
               .AddViewLocalization(LanguageViewLocationExpanderFormat.Suffix)
               .AddDataAnnotationsLocalization();

            services.AddAutoMapper(typeof(Startup));
            services.AddDefaultIdentity<UserEntity>()
                .AddRoles<IdentityRole<Guid>>()
                .AddRoleManager<RoleManager<IdentityRole<Guid>>>()
                .AddEntityFrameworkStores<ApplicationDbContext>();
            services.AddBusinessLogic(Configuration);
            services.AddControllersWithViews();
            services.AddSingleton<IStringLocalizer, SqlStringLocalizer>();

            services.AddSingleton<IStringLocalizerFactory, SqlStringLocalizerFactory>();

            services.AddSingleton(sp =>
            {
                return new SqlStringLocalizerFactory(sp);
            });
            services.AddRazorPages();

            services.Configure<RequestLocalizationOptions>(options =>
            {
                var supportedCultures = new[]
                {
                    new CultureInfo("ro"),
                    new CultureInfo("en")
                };
                options.DefaultRequestCulture = new RequestCulture(culture: "en", uiCulture: "en");
                options.SupportedCultures = supportedCultures;
                options.SupportedUICultures = supportedCultures;

                var defaultCookieRequestProvider =
                    options.RequestCultureProviders.FirstOrDefault(rcp =>
                        rcp.GetType() == typeof(CookieRequestCultureProvider));

                if (defaultCookieRequestProvider != null)
                    options.RequestCultureProviders.Remove(defaultCookieRequestProvider);

                options.RequestCultureProviders.Insert(0,
                    new CookieRequestCultureProvider()
                    {
                        CookieName = Constants.Constants.AspNetCoreCulture,
                        Options = options
                    });
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseDatabaseErrorPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }
            app.UseHttpsRedirection();
            app.UseStaticFiles();
            app.UseRouting();

            app.UseAuthentication();
            app.UseAuthorization();

            var localizationOptions = app.ApplicationServices.GetService<IOptions<RequestLocalizationOptions>>();

            app.UseRequestLocalization(localizationOptions.Value);

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllerRoute(
                    name: "default",
                    pattern: "{controller=Home}/{action=Index}/{id?}");
                endpoints.MapRazorPages();
            });
        }
    }
}
