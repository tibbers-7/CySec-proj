export class ProjectWorkDTO {
    id: number = 0
    projectID: number = 0
    engineerID: number = 0
    startDate: string = ''
    endDate: string = ''
    responsibility: string = ''

    public constructor(obj?: any) {
        if (obj) {
            this.id = obj.id;
            this.projectID = obj.projectID;
            this.engineerID = obj.engineerID;
            this.startDate = obj.startDate;
            this.endDate = obj.endDate;
            this.responsibility = obj.responsibility;   
        }
    }
}
