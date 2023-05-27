export class Project {
   id: number = 0
   name: string = ''
   startDate: string = ''
   endDate: string = ''
   projectManagerID: number = 0

   public constructor(obj?: any) {
    if (obj) {
        this.id = obj.id;
        this.name = obj.name;
        this.startDate = obj.startDate;
        this.endDate = obj.endDate;
        this.projectManagerID = obj.projectManagerID;      
    }
}

}
