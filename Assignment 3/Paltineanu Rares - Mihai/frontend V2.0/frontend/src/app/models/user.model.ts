import { Role } from "./role.model";
import { UserAdd } from "./user-add.model";

export class User {
    id?: number;
    username?: string;
    password?: string;
    email?: string;
    roles?: Role[];

    constructor(userAdd: UserAdd) {
        this.username = userAdd.username ?? '';
        this.password = userAdd.password ?? '';
        this.email = userAdd.email ?? '';
        this.roles = this.getRoles(userAdd);
    }

    getRoles(userAdd: UserAdd): Role[] {
        if(userAdd.roles?.length === 0)
            return [];
        let roles: Role[] = [];
        userAdd.roles?.forEach(role => {
            roles.push(new Role(role));
        })
        return roles;
    }
}