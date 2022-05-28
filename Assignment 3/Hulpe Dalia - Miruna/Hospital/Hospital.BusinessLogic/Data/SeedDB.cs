using Hospital.BusinessLogic.Constants;
using Hospital.Data;
using Hospital.Repository.Entitys;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Data
{
    public static class SeedDB

    {
        public async static Task Initialize(ApplicationDbContext context, RoleManager<IdentityRole<Guid>> roleManager, UserManager<UserEntity> userManager)
        {
            context.Database.EnsureCreated();

            await CreateRolesAsync(roleManager, context);
            await CreateAdminAsync(context, userManager);
        }

        #region Private


        private async static Task CreateRolesAsync(RoleManager<IdentityRole<Guid>> roleManager, ApplicationDbContext context)
        {
            var isDoctorRoleExistent = await roleManager.RoleExistsAsync(RoleConstants.Doctor);

            var isAdminRoleExistent = await roleManager.RoleExistsAsync(RoleConstants.Administrator);

            var isPacientRoleExistent = await roleManager.RoleExistsAsync(RoleConstants.Pacient);


            if (!isDoctorRoleExistent)
            {
                await roleManager.CreateAsync(new IdentityRole<Guid>(RoleConstants.Doctor));
            }

            if (!isAdminRoleExistent)
            {
                await roleManager.CreateAsync(new IdentityRole<Guid>(RoleConstants.Administrator));
            }

            if (!isPacientRoleExistent)
            {
                await roleManager.CreateAsync(new IdentityRole<Guid>(RoleConstants.Pacient));
            }

            context.SaveChanges();
        }

        private static async Task CreateAdminAsync(ApplicationDbContext context, UserManager<UserEntity> userManager)
        {
            var result = await context.Users.FirstOrDefaultAsync(e => e.Email.Equals(AccountConstants.Email));

            if (result != null)
            {
                return;
            }

            var user = new UserEntity
            {

                Email = AccountConstants.Email,
                EmailConfirmed = true,
                LockoutEnabled = true,
                NormalizedEmail = AccountConstants.NormalizedEmail,
                NormalizedUserName = AccountConstants.NormalizedEmail,
                UserName = AccountConstants.Email
            };

            var insertResult = await userManager.CreateAsync(user, "Aaa123!");

            if (!insertResult.Succeeded)
            {
                return;
            }

            context.SaveChanges();

            UserEntity dbUser = context.ApplicationUsers.FirstOrDefault(element => element.Email.Equals(user.Email));

            await userManager.AddToRoleAsync(user, RoleConstants.Administrator);

            context.SaveChanges();
        }

        #endregion

    }
}
