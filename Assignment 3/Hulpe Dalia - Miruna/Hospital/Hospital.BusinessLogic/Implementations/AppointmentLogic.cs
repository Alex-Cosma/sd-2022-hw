using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using PdfSharp.Drawing;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Implementations
{
    public class AppointmentLogic : IAppointmentLogic
    {
        public readonly IAppointmentRepository _appointmentRepository;
        public readonly IUserRepository _userRepository;
        public readonly IMapper _mapper;

        public AppointmentLogic(IAppointmentRepository appointmentRepository, IMapper mapper, IUserRepository userRepository)
        {
            _appointmentRepository = appointmentRepository;
            _mapper = mapper;
            _userRepository = userRepository;
        }

        public async Task AddNewAppointmentAsync(AppointmentModel appointment)
        {
            var doctorEntity = await _userRepository.FindFullByIdAsync(appointment.DoctorId);
            var clientEntity = await _userRepository.FindFullByIdAsync(appointment.Client.Id);

            var appointmentEntity = _mapper.Map<AppointmentEntity>(appointment);

            appointmentEntity.Hospital = doctorEntity.Hospital;
            appointmentEntity.Doctor = doctorEntity;
            appointmentEntity.Client = clientEntity;
            appointmentEntity.DateTime = appointment.DateTime;

            await _appointmentRepository.AddAsync(appointmentEntity);
        }

        public async Task<List<AppointmentModel>> GetAllAsync()
        {
            var appointmentEntitys = await _appointmentRepository.GetAllFullAsync();

            var appointments = appointmentEntitys.Select(a => _mapper.Map<AppointmentModel>(a)).ToList();

            return appointments;
        }

        public async Task DeleteAppointment(Guid id)
        {
            await _appointmentRepository.DeleteAsync(id);
        }

        public async Task<AppointmentModel> FindByIdAsync(Guid id)
        {
            var appointmentEntity = await _appointmentRepository.FindByIdFullAsync(id);

            var appointmentModel = _mapper.Map<AppointmentModel>(appointmentEntity);

            return appointmentModel;
        }

        public void UpdateAppointment(AppointmentModel newHospitalModel)
        {
            var appointmentEntity = _mapper.Map<AppointmentEntity>(newHospitalModel);

            var entity = _appointmentRepository.Update(appointmentEntity);
        }

        public void CreatePdf(string pdfName, string feedback)
        {
            var document = new PdfSharp.Pdf.PdfDocument();

            document.Info.Title = "Feedback from doctor";

            var page = document.AddPage();

            var gfx = XGraphics.FromPdfPage(page);

            var font = new XFont("TimesNewRoman", 12, XFontStyle.Regular);

            gfx.DrawString(feedback, font, XBrushes.Black, new XPoint(20, 70));

            var filename = pdfName;
            document.Save(filename);
        }
    }
}
