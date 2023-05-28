export class SkillDTO {
    id: number = 0
    engineerID: number = 0
    name: string = ''
    evaluation: number = 0

    public constructor(obj?: any) {
        if (obj) {
            this.id = obj.id;
            this.engineerID = obj.engineerID;
            this.name = obj.name;
            this.evaluation = obj.evaluation;     
        }
    }
}
