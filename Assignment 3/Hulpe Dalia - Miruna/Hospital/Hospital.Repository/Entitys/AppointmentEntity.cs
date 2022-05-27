using Hospital.Repository.Data;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Hospital.Repository.Entitys
{
    public class AppointmentEntity : IEntity
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { set; get; }

        public DateTime DateTime { set; get; }

        public Guid? ClientId { set; get; }

        public UserEntity Client { set; get; }

        public Guid? DoctorId { set; get; }

        public UserEntity Doctor { set; get; }

        public string Description { set; get; }

        public Guid? HospitalId { set; get; }

        public HospitalEntity Hospital { set; get; }
    }
}
