using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Hospital.Repository.Migrations
{
    public partial class Addlocalization : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "LocalizationRecords",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    Key = table.Column<string>(nullable: true),
                    ResourceKey = table.Column<string>(nullable: true),
                    Value = table.Column<string>(nullable: true),
                    Culture = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_LocalizationRecords", x => x.Id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "LocalizationRecords");
        }
    }
}
