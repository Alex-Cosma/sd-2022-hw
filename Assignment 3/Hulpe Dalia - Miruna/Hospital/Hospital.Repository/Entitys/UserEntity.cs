using Hospital.Repository.Data;
using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;

namespace Hospital.Repository.Entitys
{
    public class UserEntity : IdentityUser<Guid>, IEntity
    {
        public string FirstName { set; get; }

        public string LastName { set; get; }

        public string PNC { set; get; }

        public Guid? DepartmentId { set; get; }

        public DepartmentEntiy Department { set; get; }

        public Guid? HospitalId { set; get; }

        public HospitalEntity Hospital { set; get; }

        public ICollection<AppointmentEntity> Appointments { set; get; }
    }
}
