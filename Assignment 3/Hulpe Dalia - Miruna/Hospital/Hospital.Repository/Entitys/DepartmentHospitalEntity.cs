using Hospital.Repository.Data;
using System;

namespace Hospital.Repository.Entitys
{
    public class DepartmentHospitalEntity : IEntity
    {
        public Guid DepartmentId { set; get; }

        public DepartmentEntiy Department { set; get; }

        public Guid HospitalId { set; get; }

        public HospitalEntity Hospital { set; get; }
    }
}
