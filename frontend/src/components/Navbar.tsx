import {cn} from "@/lib/utils.ts";
import {Button} from "@/components/ui/button.tsx";

export const Navbar = ({className} : { className: string }) => {
    return (
        <div className={cn("flex flex-row justify-between", className)}>
            <h1 className={"my-auto ml-5 text-xl"}>My glorious blog site</h1>
            <Button className={"my-auto mr-5"}>Login</Button>
        </div>
    )
}