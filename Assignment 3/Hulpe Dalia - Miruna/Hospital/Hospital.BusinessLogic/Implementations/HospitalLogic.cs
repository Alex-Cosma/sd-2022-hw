using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Implementations
{
    public class HospitalLogic : IHospitalLogic
    {
        public readonly IHospitalRepository _hospitalRepository;
        public readonly IMapper _mapper;
        public readonly IUserRepository _userRepository;
        public readonly UserManager<UserEntity> _userManager;

        public HospitalLogic(IHospitalRepository hospitalRepository, IMapper mapper, IUserRepository userRepository, UserManager<UserEntity> userManager)
        {
            _hospitalRepository = hospitalRepository;
            _mapper = mapper;
            _userRepository = userRepository;
            _userManager = userManager;
        }

        public async Task AddNewHospitalAsync (HospitalModel hospital)
        {
            var hospitalEntity = _mapper.Map<HospitalEntity>(hospital);

            await _hospitalRepository.AddAsync(hospitalEntity);
        }

        public async Task<List<HospitalModel>> GetAllAsync()
        {
            var hospitalEntitys = await _hospitalRepository.GetAllAsync();

            var hospitals = hospitalEntitys.Select(b => _mapper.Map<HospitalModel>(b)).ToList();

            return hospitals;
        }

        public async Task<HospitalModel> FindByIdAsync(Guid id)
        {
            var hospitalEntity = await _hospitalRepository.FindByIdAsync(id);

            var hospitalModel = _mapper.Map<HospitalModel>(hospitalEntity);

            return hospitalModel;
        }

        public void UpdateHospital(HospitalModel newHospitalModel)
        {
            var hospitalEntity = _mapper.Map<HospitalEntity>(newHospitalModel);

            var entity = _hospitalRepository.Update(hospitalEntity);
        }

        public async Task<HospitalModel> FindFullAsync(Guid id)
        {
            var hospitalEntity = await _hospitalRepository.FindFullAsync(id);

            var departments = hospitalEntity.DepartmentHospital.Select(d => _mapper.Map<DepartmentModel>(d.Department));

            var doctors = hospitalEntity.Doctors.Select(d => _mapper.Map<UserModel>(d)).ToList();

            HospitalModel hospital = new HospitalModel()
            {
                Id = hospitalEntity.Id,
                Name = hospitalEntity.Name,
                Address = hospitalEntity.Address,
                Departments = departments,
                Doctors = doctors
            };

            return hospital;
        }

        public async Task AddDoctorsAsync(Guid doctorId, Guid hospitalId)
        {
            var doctorEntity = await _userRepository.FindByIdAsync(doctorId);

            var hospitalEntity = await _hospitalRepository.FindFullAsync(hospitalId);

            hospitalEntity.Doctors.Add(doctorEntity);

            _hospitalRepository.Update(hospitalEntity);
        }

        public async Task DeleteHospitalAsync(Guid id)
        {
            await _hospitalRepository.DeleteAsync(id);
        }
    }
}
