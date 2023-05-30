import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-roles-and-permissions',
  templateUrl: './roles-and-permissions.component.html',
  styleUrls: ['./roles-and-permissions.component.css']
})
export class RolesAndPermissionsComponent implements OnInit {

  allPermissions = []
  public constructor(){}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }


}
