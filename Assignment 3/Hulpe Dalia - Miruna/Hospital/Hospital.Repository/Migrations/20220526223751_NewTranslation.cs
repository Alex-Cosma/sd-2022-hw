using Hospital.Repository.Constants;
using Microsoft.EntityFrameworkCore.Migrations;
using System;
using System.Collections.Generic;

namespace Hospital.Repository.Migrations
{
    public partial class NewTranslation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            List<object[]> localizedResources = CreateLocalizedResources();
            foreach (var resource in localizedResources)
            {
                migrationBuilder.InsertData(LocalizationRecordsConstants.TableName, LocalizationRecordsConstants.ColumnNames, resource);
            }
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {

        }

        #region Private

        private List<object[]> CreateLocalizedResources()
        {
            List<object[]> localizedResources = new List<object[]>();
            localizedResources.Add(new object[] { Guid.NewGuid(), "privacy", "Title", "Just an example for localization and tranzlation", "en" });
            localizedResources.Add(new object[] { Guid.NewGuid(), "privacy", "Title", "Doar un exemplu pentru localizare si traducere", "ro" });

            localizedResources.Add(new object[] { Guid.NewGuid(), "privacy", "Text", "This text was translated. Multiple languages can be added in the future.", "en" });
            localizedResources.Add(new object[] { Guid.NewGuid(), "privacy", "Text", "Acest text a fost rtadus. Mai multe limbi pot fi adaugate in viitor.", "ro" });
            return localizedResources;
        }

        #endregion
    }
}
