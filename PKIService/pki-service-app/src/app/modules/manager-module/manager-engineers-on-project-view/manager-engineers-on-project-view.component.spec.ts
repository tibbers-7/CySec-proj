import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerEngineersOnProjectViewComponent } from './manager-engineers-on-project-view.component';

describe('ManagerEngineersOnProjectViewComponent', () => {
  let component: ManagerEngineersOnProjectViewComponent;
  let fixture: ComponentFixture<ManagerEngineersOnProjectViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerEngineersOnProjectViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerEngineersOnProjectViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
