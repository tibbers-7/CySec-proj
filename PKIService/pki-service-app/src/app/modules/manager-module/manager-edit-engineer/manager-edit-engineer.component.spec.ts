import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerEditEngineerComponent } from './manager-edit-engineer.component';

describe('ManagerEditEngineerComponent', () => {
  let component: ManagerEditEngineerComponent;
  let fixture: ComponentFixture<ManagerEditEngineerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerEditEngineerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerEditEngineerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
