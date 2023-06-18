import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SkillDTO } from 'src/app/model/skill-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CvService } from 'src/app/services/cv.service';
import { SkillService } from 'src/app/services/skill.service';

@Component({
  selector: 'app-engineer-skills',
  templateUrl: './engineer-skills.component.html',
  styleUrls: ['./engineer-skills.component.css']
})
export class EngineerSkillsComponent implements OnInit{

  public dataSource = new MatTableDataSource<SkillDTO>()
  public displayedColumns = ['id','name','evaluation', 'update-button', 'delete-button']
  public skills: SkillDTO[] = [];
  public engineerID: any

  public pdfName: string = ''

  retrieveResonse: any;
  base64Response: any;

  public constructor(private skillService: SkillService, private toast: ToastrService, private router: Router, private route: ActivatedRoute, private authService: AuthenticationService, private fileService: CvService){}


  ngOnInit(): void {
    this.engineerID = Number(this.authService.getUserId());

    this.skillService.getAllSkillsByEngineerID(this.engineerID).subscribe(res=>{
      this.skills = res;
      this.dataSource.data = this.skills;
    })

    this.fileService.downloadCV(this.engineerID).subscribe(res=>{
      if(res != null){
        this.pdfName = res.docName;
      }
      else{
        this.pdfName = 'nema pdf-a'
      }
    })
  }


  addSkill(){
    this.router.navigate(['engineer/skills/add']);
  }

  updateSkill(id: number){
    this.router.navigate(['engineer', 'skills', id, 'update']);
  }

  deleteSkill(id: number){
    this.skillService.deleteSkill(id).subscribe(res=>{
      this.skillService.getAllSkillsByEngineerID(this.engineerID).subscribe(res=>{
        this.skills = res;
        this.dataSource.data = this.skills;
      })
    })
  }

  handleCVUpload(event: any) {
    const file = event.target.files[0];
    console.log(file)
    this.fileService.uploadCV(file, this.engineerID).subscribe(
      response => {
        this.toast.success("File uploaded!");
      },
      error => {
        console.error('Error when uploading cv:', error);
      }
    );
  }

  openCV() {

    this.fileService.downloadCV(this.engineerID).subscribe(
      res => {
        this.retrieveResonse = res;
        console.log(res)
        this.processFileResponse(this.retrieveResonse.docData, this.retrieveResonse.docName)
      }); 

  }

  private processFileResponse(fileResponseData: any, fileName: string): void {

      this.base64Response = fileResponseData;
      const binaryString = window.atob(fileResponseData);
      const bytes = new Uint8Array(binaryString.length);
      const binaryToBlob = bytes.map((byte, i) => binaryString.charCodeAt(i));
      const blob = new Blob([binaryToBlob], { type: 'application/pdf' });
      this.downloadFile(blob, fileName);

  }
  
  private downloadFile(blob: any, fileName: string): void {
  
    // Other Browsers
    const url = (window.URL || window.webkitURL).createObjectURL(blob);
    window.open(url, '_blank');
  
    // rewoke URL after 15 minutes
    setTimeout(() => {
      window.URL.revokeObjectURL(url);
    }, 15 * 60 * 1000);
  }

}
