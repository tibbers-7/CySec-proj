import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEngineerOnProjectComponent } from './add-engineer-on-project.component';

describe('AddEngineerOnProjectComponent', () => {
  let component: AddEngineerOnProjectComponent;
  let fixture: ComponentFixture<AddEngineerOnProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEngineerOnProjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEngineerOnProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
