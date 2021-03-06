import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {DxDataGridComponent} from "devextreme-angular";
import { UserService } from "src/app/api/services/user.service";
import { UserGrid } from "src/app/models/user-grid.model";
import { User } from "src/app/models/user.model";

@Component({
  selector: 'app-user-toolbar',
  templateUrl :'./toolbar.component.html',
  styleUrls : ['toolbar.component.css']
})
export class UserToolbarComponent {

  // @ts-ignore
  @Input() grid: DxDataGridComponent;

  buttonName: string = 'View videos';

  urlPage: string = '/admin/videos';
  constructor(private userService: UserService,
    private router: Router) {
  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }

  public onAddUser(): void {
    this.router.navigate(['/admin/add-user']);
  }

  public onDeleteUser(): void {
    let user = JSON.parse(localStorage.getItem('user')!);
    if(this.grid.selectedRowKeys[0].username === user.name) {
      alert('You can not delete yourself')
      return
    }
    this.userService.deleteUser(this.grid.selectedRowKeys[0].id).subscribe(() => {
      this.updateUsersGrid()});
    ;
  }

  public changePage(): void {
    this.router.navigate([this.urlPage]);
  }

  private updateUsersGrid() {
    this.userService.getUsers().subscribe(users => {
      let usersGrid: UserGrid[] = [];
      users.forEach(user => {
          usersGrid.push(this.convertUser(user));
      })
      this.grid.dataSource = usersGrid;
    });
  }

  private convertUser(user: User): UserGrid {
      let userGrid: UserGrid = new UserGrid(user);
      userGrid.roleNames = user.roles?.map(role => role.name).join('; ');
      return userGrid;
  }

  get areUsersSelected(): boolean {
    if(this.grid == null) {
      return false;
    }
    return this.grid.instance.getSelectedRowsData().length > 0;
  }
}
