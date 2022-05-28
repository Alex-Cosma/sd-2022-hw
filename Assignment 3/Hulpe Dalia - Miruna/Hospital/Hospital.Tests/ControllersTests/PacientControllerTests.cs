using Hospital.Controllers;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Threading.Tasks;

namespace Hospital.Tests.ControllersTests
{
    [TestClass]
    public class PacientControllerTests
    {
        public readonly PacientController _pacientController;

        public PacientControllerTests(PacientController pacientController)
        {
            _pacientController = pacientController;
        }

        [TestMethod]
        public async Task AddAppointmentGetTest()
        {

        }

        [TestMethod]
        public async Task AddAppointmentPostTest()
        {

        }

        [TestMethod]
        public async Task DeleteAppointmentGetTest()
        {

        }
    }
}
