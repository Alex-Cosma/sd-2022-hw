using Hospital.Repository.Entitys;
using Hospital.Repository.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using ParkingProject.Repository.Extensions;
using System;
using System.Collections.Generic;
using System.Text;

namespace Hospital.Data
{
    public class ApplicationDbContext : IdentityDbContext<UserEntity, IdentityRole<Guid>, Guid>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        public DbSet<AppointmentEntity> Appointments { set; get; }

        public DbSet<DepartmentEntiy> Departments { set; get; }

        public DbSet<HospitalEntity> Hospitals { set; get; }

        public DbSet<UserEntity> ApplicationUsers { set; get; }

        public DbSet<LocalizationResourceEntity> LocalizationRecords { get; set; }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<LocalizationResourceEntity>().HasKey(m => m.Id);

            builder.ApplyConfigurationsFromAssembly(typeof(ApplicationDbContext).Assembly);
            builder.ConfigureManyToManyRelationships();
            builder.ConfigureOneToManyRelationships();

            base.OnModelCreating(builder);
        }
    }
}
