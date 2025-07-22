import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PoissonService, Poisson } from '../../services/poisson.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-list-poissons',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './list-poissons.component.html',
  styleUrls: ['./list-poissons.component.css'],
})
export class ListPoissonsComponent implements OnInit {
  poissons: Poisson[] = [];
  poissonSelectionne: Poisson | null = null;
  formPoisson!: FormGroup;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  modeAjout: boolean = false;

  constructor(private poissonService: PoissonService, private fb: FormBuilder) {}

  ngOnInit() {
    this.chargerPoissons();
    this.initForm();
  }

  initForm() {
    this.formPoisson = this.fb.group({
      id: [''],
      nom: ['', Validators.required],
      poids: [0, [Validators.required, Validators.min(0.1)]],
      espece: ['', Validators.required],
      prix: [0, [Validators.required, Validators.min(1)]],
      imageUrl: ['']
    });
  }

  chargerPoissons() {
    this.poissonService.getAll().subscribe(data => {
      this.poissons = data;
    });
  }

  ouvrirFormulaire(poisson: Poisson) {
    this.poissonSelectionne = poisson;
    this.modeAjout = false;
    this.formPoisson.patchValue(poisson);
    this.imagePreview = '/' + poisson.imageUrl;
    this.selectedFile = null;
  }

  fermerFormulaire() {
    this.poissonSelectionne = null;
    this.modeAjout = false;
    this.formPoisson.reset();
    this.imagePreview = null;
    this.selectedFile = null;
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) {
      this.selectedFile = null;
      return;
    }
    this.selectedFile = input.files[0];

    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(this.selectedFile);
  }

  enregistrerModification() {
    if (this.formPoisson.invalid) return;

    const formValue = this.formPoisson.value;

    if (this.selectedFile && this.poissonSelectionne) {
      this.poissonService.updatePoissonWithImage(this.poissonSelectionne.id!, formValue, this.selectedFile)
        .subscribe({
          next: () => {
            this.fermerFormulaire();
            this.chargerPoissons();
          },
          error: (err) => {
            alert('Erreur lors de la modification avec image');
            console.error(err);
          }
        });
    } else {
      this.poissonService.update(formValue)
        .subscribe({
          next: () => {
            this.fermerFormulaire();
            this.chargerPoissons();
          },
          error: (err) => {
            alert('Erreur lors de la modification');
            console.error(err);
          }
        });
    }
  }

  supprimerPoisson(id?: number) {
    if (!id) return;

    if (!confirm('Voulez-vous vraiment supprimer ce poisson ?')) {
      return;
    }

    this.poissonService.delete(id).subscribe({
      next: () => {
        this.chargerPoissons();
        alert('Poisson supprimé avec succès');
      },
      error: (err) => {
        alert('Erreur lors de la suppression');
        console.error(err);
      }
    });
  }

  ouvrirFormulairePourAjout() {
    this.poissonSelectionne = null;
    this.modeAjout = true;
    this.formPoisson.reset();
    this.imagePreview = null;
    this.selectedFile = null;
  }

  ajouterPoisson() {
    if (this.formPoisson.invalid) return;

    if (!this.selectedFile) {
      alert("Veuillez sélectionner une image pour le nouveau poisson.");
      return;
    }

    const formValue = this.formPoisson.value;

    this.poissonService.addPoissonWithImage(formValue, this.selectedFile)
      .subscribe({
        next: () => {
          this.fermerFormulaire();
          this.chargerPoissons();
        },
        error: (err) => {
          alert('Erreur lors de l\'ajout du poisson');
          console.error(err);
        }
      });
  }
}
