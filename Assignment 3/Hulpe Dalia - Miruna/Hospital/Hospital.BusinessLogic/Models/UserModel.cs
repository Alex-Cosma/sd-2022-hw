using System;
using System.Collections.Generic;

namespace Hospital.BusinessLogic.Models
{
    public class UserModel
    {
        public Guid Id { get; set; }

        public string Email { set; get; }

        public string FirstName { set; get; }

        public string LastName { set; get; }

        public string PNC { set; get; }

        public Guid DepartmentId { set; get; }

        public DepartmentModel Department { set; get; }

        public Guid HospitalId { set; get; }

        public HospitalModel Hospital { set; get; }

        public ICollection<AppointmentModel> Appointments { set; get; }
    }
}
