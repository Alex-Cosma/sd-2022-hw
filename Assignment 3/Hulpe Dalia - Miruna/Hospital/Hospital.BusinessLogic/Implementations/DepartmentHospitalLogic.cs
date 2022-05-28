using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using System;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Implementations
{
    public class DepartmentHospitalLogic : IDepartmentHospitalLogic
    {
        public readonly IDepartmentHospitalRepository _departmentHospitalRepository;
        public readonly IHospitalRepository _hospitalRepository;
        public readonly IDepartmentRepository _deparmtentRepository;
        public readonly IMapper _mapper;

        public DepartmentHospitalLogic(IDepartmentHospitalRepository departmentHospitalRepository, IMapper mapper, IHospitalRepository hospitalRepository, IDepartmentRepository deparmtentRepository)
        {
            _departmentHospitalRepository = departmentHospitalRepository;
            _mapper = mapper;
            _hospitalRepository = hospitalRepository;
            _deparmtentRepository = deparmtentRepository;
        }

        public async Task AddNewDepartmentHospitalAsync(Guid departmentId, Guid hospitalId)
        {
            var departmentEntity = await _deparmtentRepository.FindByIdAsync(departmentId);
            var hospitalEntity = await _hospitalRepository.FindByIdAsync(hospitalId);

            var departmentHospital = new DepartmentHospitalEntity()
            {
                Department = departmentEntity,
                DepartmentId = departmentEntity.Id,
                Hospital = hospitalEntity,
                HospitalId = hospitalEntity.Id
            };

            await _departmentHospitalRepository.AddAsync(departmentHospital);
        }
    }
}
