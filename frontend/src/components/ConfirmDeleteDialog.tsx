import {Button} from "@/components/ui/button"
import {
    Dialog,
    DialogClose,
    DialogContent,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog"

interface ConfirmDeleteDialogProps {
    label: string
    caption: string,
    handleConfirm: () => void
}

export const ConfirmDeleteDialog = ( {label, caption, handleConfirm} : ConfirmDeleteDialogProps) => {
    return (
        <Dialog>
                <DialogTrigger asChild>
                    <Button variant="outline">{label}</Button>
                </DialogTrigger>
                <DialogContent className="sm:max-w-[425px]">
                    <DialogHeader>
                        <DialogTitle>{caption}</DialogTitle>
                    </DialogHeader>
                    <DialogFooter>
                        <DialogClose asChild>
                            <Button variant={"outline"}>Cancel</Button>
                            <Button variant={"destructive"} onClick={handleConfirm}>Confirm</Button>
                        </DialogClose>
                    </DialogFooter>
                </DialogContent>
        </Dialog>
    )
}
