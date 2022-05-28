using AutoMapper;
using Hospital.BusinessLogic.Models;
using Hospital.Models;
using Hospital.Repository.Entitys;

namespace BookStore.Mappers
{
    public class Profiler: Profile
    {
        public Profiler()
        {
            #region BusinessToView

            CreateMap<UserModel, UserViewModel>();

            CreateMap<DepartmentModel, DepartmentViewModel>();

            CreateMap<HospitalModel, HospitalViewModel>();

            CreateMap<DepartmentHospitalModel, DepartmentHospitalViewModel>();

            CreateMap<AppointmentModel, AppointmentViewModel>();

            #endregion

            #region ViewToBusiness

            CreateMap<UserViewModel, UserModel>();

            CreateMap<DepartmentViewModel, DepartmentModel > ();

            CreateMap<HospitalViewModel, HospitalModel > ();

            CreateMap<DepartmentHospitalViewModel, DepartmentHospitalModel>();

            CreateMap<AppointmentViewModel, AppointmentModel > ();

            #endregion

            #region BusinessToRepo

            CreateMap<UserModel, UserEntity>();

            CreateMap<DepartmentModel, DepartmentEntiy>();

            CreateMap<HospitalModel, HospitalEntity>();

            CreateMap<DepartmentHospitalModel, DepartmentHospitalEntity>();

            CreateMap<AppointmentModel, AppointmentEntity>();

            #endregion

            #region RepoToBusiness

            CreateMap<UserEntity, UserModel>();

            CreateMap<DepartmentEntiy, DepartmentModel>();

            CreateMap<HospitalEntity, HospitalModel>();

            CreateMap<DepartmentHospitalEntity, DepartmentHospitalModel>();

            CreateMap<AppointmentEntity, AppointmentModel>();

            #endregion
        }
    }
}
