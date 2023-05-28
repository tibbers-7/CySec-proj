import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEngineerOnProjectComponent } from './edit-engineer-on-project.component';

describe('EditEngineerOnProjectComponent', () => {
  let component: EditEngineerOnProjectComponent;
  let fixture: ComponentFixture<EditEngineerOnProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditEngineerOnProjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditEngineerOnProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
