using Hospital.Repository.Constants;
using Microsoft.EntityFrameworkCore.Migrations;
using System;
using System.Collections.Generic;

namespace Hospital.Repository.Migrations
{
    public partial class Translationforwelcome : Migration
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
            localizedResources.Add(new object[] { Guid.NewGuid(), "portal", "Welcome", "Welcome!", "en" });
            localizedResources.Add(new object[] { Guid.NewGuid(), "portal", "Welcome", "Buna ziua!", "ro" });

            return localizedResources;
        }

        #endregion
    }
}
