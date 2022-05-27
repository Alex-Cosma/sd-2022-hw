using System;

namespace Hospital.BusinessLogic.Models
{
    public class DepartmentHospitalModel
    {
        public Guid DepartmentId { set; get; }

        public DepartmentModel Department { set; get; }

        public Guid HospitalId { set; get; }

        public HospitalModel Hospital { set; get; }
    }
}
