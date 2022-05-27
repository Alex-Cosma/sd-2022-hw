using Hospital.Repository.Data;
using Hospital.Repository.Models;
using System.Collections.Generic;

namespace Hospital.Repository.Interfaces
{
    public interface ILocalizationResourceRepository : IRepository<LocalizationResourceEntity>
    {
        Dictionary<string, string> GetAllFromDatabaseForResource(string resourceKey);
    }
}
