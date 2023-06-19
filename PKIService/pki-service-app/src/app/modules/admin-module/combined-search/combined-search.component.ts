import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { EmployeeDTO } from 'src/app/model/employee-dto';
import { EngineerSearchDTO } from 'src/app/model/engineer-search-dto';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-combined-search',
  templateUrl: './combined-search.component.html',
  styleUrls: ['./combined-search.component.css']
})
export class CombinedSearchComponent {
  dataSource = new MatTableDataSource<EmployeeDTO>()
  displayedColumns = ['name','surname','phoneNumber', 'startOfEmployment']
  searchDTO : EngineerSearchDTO = new EngineerSearchDTO()
  
  public constructor(private userService: UserService, private toast: ToastrService){}
 
  ngOnInit(): void{
    this.loadEngineers()
  }

  loadEngineers(){
    this.userService.getAllEngineers().subscribe(res =>{
      this.dataSource.data = res
      console.log(res)
    })
  }

  searchEngineers(){
    if(this.fieldsAreValid()) this.userService.searchEngineers(this.searchDTO).subscribe(res =>{
      this.dataSource.data = res
    })
    else this.toast.error("You have to fill all the search fields!")
  }

  fieldsAreValid(){
    return this.searchDTO.name !== '' 
    && this.searchDTO.surname !== '' 
    && this.searchDTO.username !== '' 
    && this.searchDTO.numberOfMonthsEmployed > 0
  }
}
