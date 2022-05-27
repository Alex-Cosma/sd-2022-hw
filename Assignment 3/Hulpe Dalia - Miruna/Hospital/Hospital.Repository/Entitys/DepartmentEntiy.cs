using Hospital.Repository.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace Hospital.Repository.Entitys
{
    public class DepartmentEntiy : IEntity
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { set; get; }

        public string Name { set; get; }

        public ICollection<DepartmentHospitalEntity> DepartmentHospital { set; get; }

        public ICollection<UserEntity> Doctors { set; get; }
    }
}