export class EmployeeDTO {
    id: number = 0
    name: string = ''
    surname: string = ''
    username: string = ''
    password: string = ''
    addressID: number = 0
    phoneNumber: string = ''
    userType: string = ''

    public constructor(obj?: any) {
        if (obj) {
            this.id = obj.id;
            this.name = obj.name;
            this.surname = obj.surname;
            this.username = obj.username;
            this.password = obj.password;
            this.addressID = obj.addressID;
            this.phoneNumber = obj.phoneNumber;
            this.userType = obj.userType;
        }
    }

}
