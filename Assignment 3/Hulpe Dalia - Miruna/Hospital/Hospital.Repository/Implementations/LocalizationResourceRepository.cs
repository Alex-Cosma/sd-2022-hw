using Hospital.Data;
using Hospital.Repository.Data.EFCore;
using Hospital.Repository.Interfaces;
using Hospital.Repository.Models;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;

namespace Hospital.Repository.Implementations
{
    public class LocalizationResourceRepository : EfCoreRepository<LocalizationResourceEntity, ApplicationDbContext>, ILocalizationResourceRepository
    {
        private readonly DbSet<LocalizationResourceEntity> _dbSet;

        public LocalizationResourceRepository(ApplicationDbContext context) : base(context)
        {
            _dbSet = context.Set<LocalizationResourceEntity>();
        }

        public Dictionary<string, string> GetAllFromDatabaseForResource(string composedKey)
        {
            string[] words = composedKey.Split('.');
            string key = words[0];
            string resourceKey;
            if (words.Count() == 1)
            {
                resourceKey = "";
            }
            else
            {
                resourceKey = words[1];
            }

            var result = _dbSet.Where(data => data.ResourceKey.Equals(resourceKey) && data.Key.Equals(key)).ToDictionary(element => $"{element.Key}.{element.Culture}", element => element.Value);
            return result;

        }
    }
}
