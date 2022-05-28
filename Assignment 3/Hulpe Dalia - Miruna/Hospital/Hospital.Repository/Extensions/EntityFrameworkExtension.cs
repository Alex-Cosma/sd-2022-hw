using Hospital.Repository.Entitys;
using Microsoft.EntityFrameworkCore;
using System;

namespace ParkingProject.Repository.Extensions
{
    public static class EntityFrameworkExtension
    {
        public static void ConfigureManyToManyRelationships(this ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<DepartmentHospitalEntity>()
               .HasKey(dh => new { dh.DepartmentId, dh.HospitalId });

            modelBuilder.Entity<DepartmentHospitalEntity>()
                .HasOne(dh => dh.Department)
                .WithMany(d => d.DepartmentHospital)
                .HasForeignKey(dh => dh.DepartmentId);

            modelBuilder.Entity<DepartmentHospitalEntity>()
                .HasOne(dh => dh.Hospital)
                .WithMany(h => h.DepartmentHospital)
                .HasForeignKey(dh => dh.HospitalId);
        }

        public static void ConfigureOneToManyRelationships(this ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<UserEntity>()
             .HasOne(u => u.Hospital)
             .WithMany(h => h.Doctors)
             .HasForeignKey(u => u.HospitalId);

            modelBuilder.Entity<UserEntity>()
             .HasOne(u => u.Department)
             .WithMany(d => d.Doctors)
             .HasForeignKey(u => u.DepartmentId)
             .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<AppointmentEntity>()
            .HasOne(a => a.Client)
            .WithMany(c => c.Appointments)
            .HasForeignKey(a => a.ClientId)
            .OnDelete(DeleteBehavior.Cascade);
        }
    }
}
