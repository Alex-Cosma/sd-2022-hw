using Hospital.Repository.Data;
using System;

namespace Hospital.Repository.Models
{
    public class LocalizationResourceEntity : IEntity
    {
        public Guid Id { get; set; }

        public string Key { get; set; }

        public string ResourceKey { get; set; }

        public string Value { get; set; }

        public string Culture { get; set; }
    }
}
