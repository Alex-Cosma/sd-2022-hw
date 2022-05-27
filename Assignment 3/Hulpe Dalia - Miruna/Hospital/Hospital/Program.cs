using Hospital.BusinessLogic.Data;
using Hospital.Data;
using Hospital.Repository.Entitys;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using System;
using System.Linq;

namespace Hospital
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var host = CreateHostBuilder(args).Build();

            InitializeDatabaseAsync(host);

            host.Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();
                });

        #region Private

        private async static void InitializeDatabaseAsync(IHost host)
        {
            using (var scope = host.Services.CreateScope())
            {
                var services = scope.ServiceProvider;

                try
                {
                    var context = services.GetRequiredService<ApplicationDbContext>();
                    var roleManager = services.GetRequiredService<RoleManager<IdentityRole<Guid>>>();
                    var userManager = services.GetRequiredService<UserManager<UserEntity>>();

                    var pendingMigrations = await context.Database.GetPendingMigrationsAsync();

                    if (pendingMigrations.Any())
                    {
                        await context.Database.MigrateAsync();
                    }

                    await SeedDB.Initialize(context, roleManager, userManager);
                }
                catch (Exception ex)
                {
                    var logger = services.GetRequiredService<ILogger<Program>>();

                    logger.LogError(ex, "An error occurred creating the DB.");
                }
            }
        }

        #endregion
    }
}
