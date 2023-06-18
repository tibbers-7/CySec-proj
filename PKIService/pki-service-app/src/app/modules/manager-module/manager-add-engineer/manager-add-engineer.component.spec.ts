import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerAddEngineerComponent } from './manager-add-engineer.component';

describe('ManagerAddEngineerComponent', () => {
  let component: ManagerAddEngineerComponent;
  let fixture: ComponentFixture<ManagerAddEngineerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerAddEngineerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerAddEngineerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
