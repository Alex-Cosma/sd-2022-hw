using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Implementations
{
    public class DepartmentLogic : IDepartmentLogic
    {
        public readonly IDepartmentRepository _departmentRepository;
        public readonly IMapper _mapper;

        public DepartmentLogic(IDepartmentRepository departmentRepository, IMapper mapper)
        {
            _departmentRepository = departmentRepository;
            _mapper = mapper;
        }

        public async Task AddNewDepartmentAsync(DepartmentModel department)
        {
            var departmentEntity = _mapper.Map<DepartmentEntiy>(department);

            await _departmentRepository.AddAsync(departmentEntity);
        }

        public async Task<List<DepartmentModel>> GetAllAsync()
        {
            var departmentsEntitys = await _departmentRepository.GetAllAsync();

            var departments = departmentsEntitys.Select(d => _mapper.Map<DepartmentModel>(d)).ToList();

            return departments;
        }

        public async Task<DepartmentModel> FindByIdAsync(Guid id)
        {
            var departmentEntity = await _departmentRepository.FindByIdAsync(id);

            var departmentModel = _mapper.Map<DepartmentModel>(departmentEntity);

            return departmentModel;
        }

        public void UpdateDepartment(DepartmentModel newDepartmentModel)
        {
            var departmentEntity = _mapper.Map<DepartmentEntiy>(newDepartmentModel);

            var entity = _departmentRepository.Update(departmentEntity);
        }

        public async Task DeleteDepartmentAsync(Guid id)
        {
            await _departmentRepository.DeleteAsync(id);
        }
    }
}
