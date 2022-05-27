using System;

namespace Hospital.BusinessLogic.Models
{
    public class AppointmentModel
    {
        public Guid Id { set; get; }

        public DateTime DateTime { set; get; }

        public Guid? ClientId { set; get; }

        public UserModel Client { set; get; }

        public Guid? DoctorId { set; get; }

        public UserModel Doctor { set; get; }

        public string Description { set; get; }

        public Guid HospitalId { set; get; }

        public HospitalModel Hospital { set; get; }

    }
}
